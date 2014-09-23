package org.test.commons.cache;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactory;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.auth.AuthDescriptor;
import net.spy.memcached.auth.PlainCallbackHandler;
import net.spy.memcached.transcoders.SerializingTranscoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.test.commons.exception.ConfigurationException;

/**
 * Memcached implementation (using http://code.google.com/p/spymemcached/)
 *
 * expiration is specified in seconds
 */
public class MemcachedImpl implements CacheImpl {

	private Log log = LogFactory.getLog(this.getClass());
	
    private static MemcachedImpl uniqueInstance;

    MemcachedClient client;

    SerializingTranscoder tc;

    public static MemcachedImpl getInstance() throws IOException {
      return getInstance(false);
    }

    public static MemcachedImpl getInstance(boolean forceClientInit) throws IOException {
        if (uniqueInstance == null) {
            uniqueInstance = new MemcachedImpl();
        } else if (forceClientInit) {
            // When you stop the client, it sets the interrupted state of this thread to true. If you try to reinit it with the same thread in this state,
            // Memcached client errors out. So a simple call to interrupted() will reset this flag
            Thread.interrupted();
            uniqueInstance.initClient();
        }
        return uniqueInstance;

    }

    private MemcachedImpl() throws IOException {
        tc = new SerializingTranscoder() {

            @Override
            protected Object deserialize(byte[] data) {
                try {
                    return new ObjectInputStream(new ByteArrayInputStream(data)) {

                        @Override
                        protected Class<?> resolveClass(ObjectStreamClass desc)
                                throws IOException, ClassNotFoundException {
                            return Class.forName(desc.getName(), false, ClassLoader.getSystemClassLoader());
                        }
                    }.readObject();
                } catch (Exception e) {
                    log.error("Could not deserialize",e);
                }
                return null;
            }

            @Override
            protected byte[] serialize(Object object) {
                try {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    new ObjectOutputStream(bos).writeObject(object);
                    return bos.toByteArray();
                } catch (IOException e) {
                    log.error("Could not serialize",e);
                }
                return null;
            }
        };
        initClient();
    }

    public void initClient() throws IOException {
        System.setProperty("net.spy.log.LoggerImpl", "net.spy.memcached.compat.log.Log4JLogger");
        
        List<InetSocketAddress> addrs;
//        Properties property = SystemUtils.loadSystemProperties();
        Properties property = null;
        if(property.containsKey("memcached.host")) {
            addrs = AddrUtil.getAddresses(property.getProperty("memcached.host"));
        } else if (property.containsKey("memcached.1.host")) {
            int nb = 1;
            String addresses = "";
            while (property.containsKey("memcached." + nb + ".host")) {
                addresses += property.get("memcached." + nb + ".host") + " ";
                nb++;
            }
            addrs = AddrUtil.getAddresses(addresses);
        } else {
            throw new ConfigurationException("Bad configuration for memcached: missing host(s)");
        }
        
        if (property.containsKey("memcached.user")) {
            String memcacheUser = property.getProperty("memcached.user");
            String memcachePassword = property.getProperty("memcached.password");
            if (memcachePassword == null) {
                throw new ConfigurationException("Bad configuration for memcached: missing password");
            }
            
            // Use plain SASL to connect to memcached
            AuthDescriptor ad = new AuthDescriptor(new String[]{"PLAIN"},
                                    new PlainCallbackHandler(memcacheUser, memcachePassword));
            ConnectionFactory cf = new ConnectionFactoryBuilder()
                                        .setProtocol(ConnectionFactoryBuilder.Protocol.BINARY)
                                        .setAuthDescriptor(ad)
                                        .build();
            
            client = new MemcachedClient(cf, addrs);
        } else {
            client = new MemcachedClient(addrs);
        }
    }

    public void add(String key, Object value, int expiration) {
        client.add(key, expiration, value, tc);
    }

    public Object get(String key) {
//        Future<Object> future = client.asyncGet(key, tc);
//        try {
//            return future.get(1, TimeUnit.SECONDS);
//        } catch (Exception e) {
//            future.cancel(false);
//        }
//        return null;
    	return client.get(key);
    }

    public void clear() {
        client.flush();
    }

    public void delete(String key) {
        client.delete(key);
    }

    public Map<String, Object> get(String[] keys) {
        Future<Map<String, Object>> future = client.asyncGetBulk(tc, keys);
        try {
            return future.get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            future.cancel(false);
        }
        return Collections.<String, Object>emptyMap();
    }

    public long incr(String key, int by) {
        return client.incr(key, by, 0);
    }

    public long decr(String key, int by) {
        return client.decr(key, by, 0);
    }

    public void replace(String key, Object value, int expiration) {
        client.replace(key, expiration, value, tc);
    }

    public boolean safeAdd(String key, Object value, int expiration) {
        Future<Boolean> future = client.add(key, expiration, value, tc);
        try {
            return future.get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            future.cancel(false);
        }
        return false;
    }

    public boolean safeDelete(String key) {
        Future<Boolean> future = client.delete(key);
        try {
            return future.get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            future.cancel(false);
        }
        return false;
    }

    public boolean safeReplace(String key, Object value, int expiration) {
        Future<Boolean> future = client.replace(key, expiration, value, tc);
        try {
            return future.get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            future.cancel(false);
        }
        return false;
    }

    public boolean safeSet(String key, Object value, int expiration) {
        Future<Boolean> future = client.set(key, expiration, value, tc);
        try {
            return future.get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            future.cancel(false);
        }
        return false;
    }

    public void set(String key, Object value, int expiration) {
        client.set(key, expiration, value, tc);
    }

    public void stop() {
        client.shutdown();
    }

	public List<Object> getKeys() {
		Map<SocketAddress, Map<String, String>> result = client.getStats("items");
		Set<String> keys =  result.get(result.keySet().iterator().next()).keySet();
		Set<String> temp = new HashSet<String>();
		for (String key : keys) {
			result = client.getStats("cachedump "+key.split(":")[1]+" 0");
			temp.addAll(result.get(result.keySet().iterator().next()).keySet());
		}
		return new ArrayList<Object>(temp);
	}
	 public void printStats(OutputStream stream) throws IOException {  
        Map<SocketAddress, Map<String, String>> statMap =   
            client.getStats();  
        if (stream == null) {  
            stream = System.out;  
        }  
        StringBuffer buf = new StringBuffer();  
        Set<SocketAddress> addrSet = statMap.keySet();  
        Iterator<SocketAddress> iter = addrSet.iterator();  
        while (iter.hasNext()) {  
            SocketAddress addr = iter.next();  
            buf.append(addr.toString() + "/n");  
            Map<String, String> stat = statMap.get(addr);  
            Set<String> keys = stat.keySet();  
            Iterator<String> keyIter = keys.iterator();  
            while (keyIter.hasNext()) {  
                String key = keyIter.next();  
                String value = stat.get(key);  
                buf.append("  key=" + key + ";value=" + value + "/n");  
            }  
            buf.append("/n");  
        }  
        stream.write(buf.toString().getBytes());  
        stream.flush();  
    }  
      
}
