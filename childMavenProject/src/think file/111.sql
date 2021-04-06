1. get field 从内存中获取count
2. count+1
3. put field 将count+1的值刷新到内存中

4. OpenFeign在这块主要做的事情是，无感知调用http请求，把http请求封装到FeignClient里面。

5. feign在自己的客户端代码里面实现远程调用的作用。

6. zuul