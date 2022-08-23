Nacos Server 还可以作为配置中心，对 Spring Cloud 应用的外部配置进行统一地集中化管理。
而我们只需要在应用的 POM 文件中引入 spring-cloud-starter-alibaba-nacos-config 即可实现配置的获取与动态刷新。

Nacos 可以说是 Spring Cloud Config 的替代方案，但相比后者 Nacos 的使用更简单，操作步骤也更少。


 启动 Nacos Server，并在 Nacos Server 控制台的“配置管理”下的“配置列表”中，点击“+”按钮，新建如下配置。
 Data ID:        config-client-dev.yaml
 
 Group  :        DEFAULT_GROUP
 
 配置格式:        YAML
 
 配置内容:      config:
                   info: c.biancheng.net
在 Nacos Server 中，配置的 dataId（即 Data ID）的完整格式如下
${prefix}：默认取值为微服务的服务名，即配置文件中 spring.application.name 的值，
    我们可以在配置文件中通过配置 spring.cloud.nacos.config.prefix 来指定。
${spring.profiles.active}：表示当前环境对应的 Profile，例如 dev、test、prod 等。
    当没有指定环境的 Profile 时，其对应的连接符也将不存在， dataId 的格式变成 ${prefix}.${file-extension}。
${file-extension}：表示配置内容的数据格式，我们可以在配置文件中通过配置项 
    spring.cloud.nacos.config.file-extension 来配置，例如 properties 和 yaml。
    
 启动8003, 访问http://localhost:8003/config/info
 
在 Nacos Server 中，将 config-client-dev.yaml 中的配置修改成如下内容
config:
    info: this is c.biancheng.net
不重启，8003, 访问http://localhost:8003/config/info