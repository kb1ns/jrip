重复造个字节码解析小轮子以深入学习Java Bytecode技术。    
Inspired by [@zxh0/classpy](https://github.com/zxh0/classpy)

## TODO
1. 未解析异常信息表
2. 未解析常量池引用
3. 未解析本地变量表
   
     
源代码:    


	import java.util.ResourceBundle;
    
    public class Hello{
        private final int h = 2;
    
        public static void main(String[] args){
            System.out.println("Hello, world.\n");
            ResourceBundle rb = ResourceBundle.getBundle("test");
            rb.getString("key");
        }
    }


解析Demo:    


	public class #10(ConstantClass)->null {
        private final #13(ConstantUtf8)->I #12(ConstantUtf8)->h
        public #16(ConstantUtf8)-><init> #17(ConstantUtf8)->()V {
        [Code:
            aload_0 
            invokespecial 00 01 
            aload_0 
            iconst_2 
            putfield 00 02 
            _return 
        ]
        }
        public static #20(ConstantUtf8)->main #21(ConstantUtf8)->([Ljava/lang/String;)V {
        [Code:
            getstatic 00 03 
            ldc 04 
            invokevirtual 00 05 
            ldc 06 
            invokestatic 00 07 
            astore_1 
            aload_1 
            ldc 08 
            invokevirtual 00 09 
            pop 
            _return 
        ]
        }
    }
    
    
javap解析:
	
	
	pixyonly::~/Workspace/idea-2016/class-ripper->$ javap -v ~/Engineering/Hello.class
    Classfile /Users/pixyonly/Engineering/Hello.class
      Last modified Apr 9, 2016; size 686 bytes
      MD5 checksum 77352977e4d91bbfd9ef2ccc0989e87a
      Compiled from "Hello.java"
    public class Hello
      minor version: 0
      major version: 52
      flags: ACC_PUBLIC, ACC_SUPER
    Constant pool:
       #1 = Methodref          #11.#24        // java/lang/Object."<init>":()V
       #2 = Fieldref           #10.#25        // Hello.h:I
       #3 = Fieldref           #26.#27        // java/lang/System.out:Ljava/io/PrintStream;
       #4 = String             #28            // Hello, world.\n
       #5 = Methodref          #29.#30        // java/io/PrintStream.println:(Ljava/lang/String;)V
       #6 = String             #31            // test
       #7 = Methodref          #32.#33        // java/util/ResourceBundle.getBundle:(Ljava/lang/String;)Ljava/util/ResourceBundle;
       #8 = String             #34            // key
       #9 = Methodref          #32.#35        // java/util/ResourceBundle.getString:(Ljava/lang/String;)Ljava/lang/String;
      #10 = Class              #36            // Hello
      #11 = Class              #37            // java/lang/Object
      #12 = Utf8               h
      #13 = Utf8               I
      #14 = Utf8               ConstantValue
      #15 = Integer            2
      #16 = Utf8               <init>
      #17 = Utf8               ()V
      #18 = Utf8               Code
      #19 = Utf8               LineNumberTable
      #20 = Utf8               main
      #21 = Utf8               ([Ljava/lang/String;)V
      #22 = Utf8               SourceFile
      #23 = Utf8               Hello.java
      #24 = NameAndType        #16:#17        // "<init>":()V
      #25 = NameAndType        #12:#13        // h:I
      #26 = Class              #38            // java/lang/System
      #27 = NameAndType        #39:#40        // out:Ljava/io/PrintStream;
      #28 = Utf8               Hello, world.\n
      #29 = Class              #41            // java/io/PrintStream
      #30 = NameAndType        #42:#43        // println:(Ljava/lang/String;)V
      #31 = Utf8               test
      #32 = Class              #44            // java/util/ResourceBundle
      #33 = NameAndType        #45:#46        // getBundle:(Ljava/lang/String;)Ljava/util/ResourceBundle;
      #34 = Utf8               key
      #35 = NameAndType        #47:#48        // getString:(Ljava/lang/String;)Ljava/lang/String;
      #36 = Utf8               Hello
      #37 = Utf8               java/lang/Object
      #38 = Utf8               java/lang/System
      #39 = Utf8               out
      #40 = Utf8               Ljava/io/PrintStream;
      #41 = Utf8               java/io/PrintStream
      #42 = Utf8               println
      #43 = Utf8               (Ljava/lang/String;)V
      #44 = Utf8               java/util/ResourceBundle
      #45 = Utf8               getBundle
      #46 = Utf8               (Ljava/lang/String;)Ljava/util/ResourceBundle;
      #47 = Utf8               getString
      #48 = Utf8               (Ljava/lang/String;)Ljava/lang/String;
    {
      public Hello();
        descriptor: ()V
        flags: ACC_PUBLIC
        Code:
          stack=2, locals=1, args_size=1
             0: aload_0
             1: invokespecial #1                  // Method java/lang/Object."<init>":()V
             4: aload_0
             5: iconst_2
             6: putfield      #2                  // Field h:I
             9: return
          LineNumberTable:
            line 3: 0
            line 4: 4
    
      public static void main(java.lang.String[]);
        descriptor: ([Ljava/lang/String;)V
        flags: ACC_PUBLIC, ACC_STATIC
        Code:
          stack=2, locals=2, args_size=1
             0: getstatic     #3                  // Field java/lang/System.out:Ljava/io/PrintStream;
             3: ldc           #4                  // String Hello, world.\n
             5: invokevirtual #5                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
             8: ldc           #6                  // String test
            10: invokestatic  #7                  // Method java/util/ResourceBundle.getBundle:(Ljava/lang/String;)Ljava/util/ResourceBundle;
            13: astore_1
            14: aload_1
            15: ldc           #8                  // String key
            17: invokevirtual #9                  // Method java/util/ResourceBundle.getString:(Ljava/lang/String;)Ljava/lang/String;
            20: pop
            21: return
          LineNumberTable:
            line 7: 0
            line 8: 8
            line 9: 14
            line 10: 21
    }
    SourceFile: "Hello.java"