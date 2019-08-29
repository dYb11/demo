#微信动态模板推送示例

ScanAllAnnoUtil ：该类在项目启动时扫描自定义注解

TemplateUtil ：模板内容替换器

WxEventMsgPram ：自定义注解

流程：
》项目启动，扫描项目中带有此自定义注解的字段，将字段和val值保存到缓存中
》