# compileWW
复旦大学本科2018年秋编译课程期末项目，为Mini Java语言构造抽象语法树并画图。<br/>
**作者**：卫艺璇（15307130433），王炜越（15307130349）<br/>
**项目依赖**：[jdk-1.8.0](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html), 
[antlr-4.7.2](https://www.antlr.org/download/antlr-4.7.2-complete.jar), [graphviz](http://www.graphviz.org/download/) <br/>
**运行平台**：macOS Mojave, terminal

### 组员分工
- **卫艺璇**：ANTLR4学习、资料收集、g4语法句法文件和流程设计、报告撰写
- **王炜越**：绘制自定义语法树、测试和修bug、增加自定义错误检查和修正、报告撰写

### 运行代码
* 下载项目到“/项目位置”
* 安装并配置项目环境：
> 安装[jdk](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)  （jdk版本>=1.8）<br/>
> 安装[antlr-4.7.2](https://www.antlr.org/download/antlr-4.7.2-complete.jar) 到“/antlr地址” <br/>
> 配置antlr4的java classpath环境变量，参照[官网](https://www.antlr.org/index.html) <br/>
* 运行项目
> terminal命令行中进入out/production/project/目录，运行java MiniJavaAnalyze命令，即开启程序 <br/>
> 输入树的显示选项（1为显示所有结点的所有代码文本，0为只显示叶子的代码文本）<br/>
> 输入MiniJava格式的源代码，Control+D结束输入 <br/>
> 画出的抽象语法树图会显示，检测的语法错误则会以log形式显示在命令行中 <br/>
