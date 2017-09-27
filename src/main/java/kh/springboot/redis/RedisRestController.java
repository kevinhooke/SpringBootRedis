package kh.springboot.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@GetMapping("values")
	public List<RedisResult> getValues(){
		List<RedisResult> results = new ArrayList<>();
		Set<String> keys = this.template.keys("*");
		for(String key : keys){
			results.add(new RedisResult(key, (String)this.template.opsForValue().get(key)));
		}
		return results;
	}
	
	@PostMapping("singlekey/{key}/{value}")
	public RedisResult addValue(@PathVariable("key") String key, @PathVariable("value") String value){
		RedisResult result = new RedisResult(key, value);
		
		this.template.opsForValue().set(key, value);
		
		return result;
	}

}
