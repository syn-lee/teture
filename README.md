# EPAM

#### 软件架构
springboot + nacos 做服务注册发现中心以及配置中心，guava cache 做内存缓存， OncePerRequestFilter 做频次限级，
支持国际化，通过地址栏添加参数 lang=locale 修改提示，统一返回值，统一错误信息

#### 介绍介绍
本项目采用微服务架构，天气提供者和使用者单独部署。

#### 设计要点
1. 服务提供方通过 okhttpclient 连接池调用第三方 url，添加拦截器实现重试，重试次数可以从配置中心动态获取
2. 调用方采用 FeigntClient 读取服务中心数据，还原为po对象
3. 地点信息不常变动，服务启动时异步同步到内存缓存（可优化到 redis 中做持久化）
4. 缓存温度等天气信息（半个小时，可配），服务降频 
5. 测试方式：
   1. 服务端url：localhost:18080/epam/weather/省/市/县
   2. 客户端url 访问天气：localhost:18081/test/weather/省/市/县
   3. 客户端url 访问温度：localhost:18081/test/省/市/县
6. snapshot
<img width="385" alt="weather1" src="https://user-images.githubusercontent.com/6193594/141064469-2726c09c-56d1-4ae7-b318-34b0d4a64429.png">
<img width="408" alt="weather2" src="https://user-images.githubusercontent.com/6193594/141064480-72479403-b460-4631-a119-8dedd0c79b64.png">
<img width="342" alt="weather3" src="https://user-images.githubusercontent.com/6193594/141064482-e7e44f82-ebc1-4885-9a78-8a6ae1c3ea85.png">


### nacos 中核心配置项，默认 8848 端口
    weather.provinceUrl=http://www.weather.com.cn/data/city3jdata/china.html
    weather.cityUrl=http://www.weather.com.cn/data/city3jdata/provshi/{id}.html
    weather.countyUrl=http://www.weather.com.cn/data/city3jdata/station/{id}.html
    weather.url=http://www.weather.com.cn/data/sk/{id}.html
    weather.timeout=10
    weather.retries=3
    service.limit=100
