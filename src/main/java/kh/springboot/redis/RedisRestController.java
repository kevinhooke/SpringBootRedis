package kh.springboot.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import kh.springboot.redis.domain.RedisResult;

@RestController
public class RedisRestController {

	@Autowired
	@Qualifier("RedisTemplate")
	private RedisTemplate<String, Object> template;

	@GetMapping("singlekey/{key}")
	public RedisResult getSingleValue(@PathVariable("key") String key){
		String value = (String)this.template.opsForValue().get(key);
		
		RedisResult result = new RedisResult(key, value);
		return result;
	}
	
}
