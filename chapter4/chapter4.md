### java条件表达式
注意在java中使用数值作为bool类型是非法的，也就是不能够写`if i:`,i为数值这种形式的判断式


### break和continue的标签
算是一个较少应用的知识点，可以在循环之前加入一些标签，标签的格式为`name:`，且这句话后面不能带有任何的
执行语句，之后再break或者continue之后加入标签名，可以指定break或continue打断或继续执行某个循环


### switch语句
每一句case后都要带有一个break，否则就会顺序执行case，知道遇到break或者default