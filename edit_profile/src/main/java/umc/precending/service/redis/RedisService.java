package umc.precending.service.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import umc.precending.exception.token.TokenNotCorrectException;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> template;

    public void saveValue(String key, String value) {
        ValueOperations<String, String> operations = template.opsForValue();
        operations.set(key, value);
    }

    public String getValue(String key) {
        ValueOperations<String, String> operations = template.opsForValue();
        return operations.get(key);
    }

    public boolean checkValidation(String key, String tokenValue) {
        ValueOperations<String, String> operations = template.opsForValue();
        String storedValue = operations.get(key);

        if(!storedValue.equals(tokenValue)) throw new TokenNotCorrectException();

        return true;
    }
}
