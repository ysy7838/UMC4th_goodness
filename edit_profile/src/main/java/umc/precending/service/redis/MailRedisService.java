package umc.precending.service.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import umc.precending.exception.email.AuthNumNotCorrectException;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class MailRedisService {
    private final StringRedisTemplate redisTemplate;

    public String getData(String key){//지정된 키(key)에 해당하는 데이터를 Redis에서 가져오는 메서드
        ValueOperations<String,String> valueOperations=redisTemplate.opsForValue();
        return valueOperations.get(key);
    }
    public void setData(String key,String value){//지정된 키(key)에 값을 저장하는 메서드
        ValueOperations<String,String> valueOperations=redisTemplate.opsForValue();
        valueOperations.set(key,value);
    }
    public void setDataExpire(String key,String value,long duration){//지정된 키(key)에 값을 저장하고, 지정된 시간(duration) 후에 데이터가 만료되도록 설정하는 메서드
        ValueOperations<String,String> valueOperations=redisTemplate.opsForValue();
        Duration expireDuration=Duration.ofSeconds(duration);
        valueOperations.set(key,value,expireDuration);
    }
    public boolean checkEmailNumValidation(String email,String AuthNum) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String storedValue = operations.get(AuthNum);
        if(storedValue==null||!storedValue.equals(email)) {
            throw new AuthNumNotCorrectException();
        }

        return true;
    }
    public void deleteData(String key){//지정된 키(key)에 해당하는 데이터를 Redis에서 삭제하는 메서드
        redisTemplate.delete(key);
    }
}
