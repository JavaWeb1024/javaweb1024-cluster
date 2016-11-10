# javaweb1024-cluster
具体详情参照 http://www.javaweb1024.com/java/JavaWebzhongji/2016/07/13/972.html
一. 架构框架说明
1)    后端技术点说明
Mysql
SpringMVC
FreeMarker
Mybatis
Kaptcha(Google验证码)
Druid(数据源)
Atomikos(数据源事物)
SpringCache(缓存集成redis)
Shrio(权限控制,可以精确到按钮级别,集成redis)
Redis(缓存服务器)
Log4j
Maven
Generator(Mybatis自动生成工具)
2)    前端技术点说明
Layer(弹出层)
Font-awesome
artTabs(前端框架)
My97DatePicker
Ueditor(百度富文本编辑器)
jquery.validationEngin(校验框架)
3)    系统目录介绍
clip_image002.jpg
Action: 控制层
Constant: 常量 (Token为模仿Struts防二次提交策略 使用方法参照TokenInterceptor)
Dao:数据层
Entity:实体类
Redis:缓存服务器相关
Task: 定时任务
Plugin &Tag:自定义标签相关
……
其余的包请查看相关类容
 
二. 系统说明
后台访问路径: http://localhost:8083/manage/ 账户/密码 admin/123456 登陆页面如下:
clip_image004.jpg
在redis缓存服务器中有相应的Key与之相应:
clip_image006.jpg 
 
登陆后的页面
clip_image008.jpg
 
三. 技术讲解
本系统采用普通的MVC架构, 采用的框架请参照架构框架说明.
系统在设置的有效时间内重启服务,用户不需要重新登录,因为shiro的Session存储在Redis中. 当Shiro检验是否需要登录时,会从Redis中获取相应的数据.
当重启数据库服务时候(mysql)服务, 系统也不需要重新启动.在架构中已经添加相应的监测. 当数据库服务器启动后,相应的业务会恢复正常状态.
Static 文件夹为静态文件,包含Js Css 图片等.这些单独为文件,用处在于后期可以通过nginx直接映射.
百度的Ueditor经过二次开发, 与系统以作完美匹配.并且修复其在管理所有图片文件的BUG,使之更好的服务架构.
Mybatis自动生成工具也是经过二次开发(mybatis-generator-core-1.3.2-javadoc.jar),添加数据库字段备注获取,对相应的Xml进行整理,提取公共部分.新增统计,获取list对象集合等方法. 在实体类中监测是否为空,采用了StringUtils 包中的isBlank方法.具体说明参考网站http://www.javaweb1024.com/info/928.jspx
redis下载win系统https://github.com/ServiceStack/redis-windows/tree/master/downloads,linux请参考本网站http://www.javaweb1024.com/data/NoSQL/2015/06/29/785.html
