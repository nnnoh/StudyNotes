```java
public class TestRestTemplate {
    @Autowired
    private RestTemplate restTemplate;

    public void postObject(String id){
        String url="http://www.baidu.com";
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("id",id);
        HttpHeaders header = new HttpHeaders();
        // 需求需要传参为form-data格式
        header.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, header);
        JSONObject response = restTemplate.postForObject(url, httpEntity, JSONObject.class);
    }
}
```

