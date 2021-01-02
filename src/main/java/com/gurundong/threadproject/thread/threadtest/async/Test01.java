package com.gurundong.threadproject.thread.threadtest.async;

import java.util.concurrent.CompletableFuture;

/**
 * CompletionStage：阶段的概念与使用。
 * 参考博文：https://www.cnblogs.com/txmfz/p/11266411.html
 * 注意：
 * 1. CompletableFuture执行默认使用的是ForkJoinPool.commonPool，该线程池为守护线程池。
 * 2. JVM的运行机制中，所有非守护线程都执行完毕后，JVM就会自动退出，JVM的退出不关心守护线程是否结束。
 * 3. 如果commonPool中的线程执行的比main线程慢，会导致每年执行完JVM就退出了，需要加join或get进行阻塞等待。
 *
 * 所有不带async的方法是在线程池中的当前线程继续运行，带async的方法会在线程池的其它线程继续运行
 */
public class Test01 {
    public static void main(String[] args) {
        Test01 test = new Test01();
//        test.test01();
//        System.out.println();
//        test.test02();
//        System.out.println();
//        test.test03();
//        System.out.println();
//        test.test04();
//        System.out.println();
//        test.test05();
//        System.out.println();
//        test.test06();
//        System.out.println();
//        test.test07();
//        System.out.println();
//        test.test08();
        System.out.println();
        test.test09();
    }

    /**
     * 类型一 ： 产出型，拿到上一步结果，处理后产生新的结果
     */
    // 产出型： 测试thenApply，拿到返回值的后续动作。单一有返回值异步
    public void test01(){
        System.out.println("test01:-------------");
        CompletableFuture<String> stage = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("线程:"+Thread.currentThread().getName()+"运行中");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "abc";
        });
        System.out.println(Thread.currentThread().getName()+"线程运行中");
        // join方法同步阻塞，等待异步方法完成
        String join = stage.thenApply(res -> res + "def").join();
        System.out.println(join);
        System.out.println("test01结束");
    }

    // 产出型： 测试thenCombine，同时等待2个异步线程结果。
    public void test02(){
        System.out.println("test02:-------------");
        CompletableFuture<String> stage1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("线程:"+Thread.currentThread().getName()+"运行中");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "abc";
        });
        CompletableFuture<String> stage2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("线程:"+Thread.currentThread().getName()+"运行中");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "def";
        });
        System.out.println(Thread.currentThread().getName()+"线程运行中");
        String join = stage1.thenCombine(stage2,(res1,res2)-> res1 + res2).join();
        System.out.println(join);
        System.out.println("test02结束");
    }

    // 产出型： 测试applyToEither，同时2个中任意一个异步线程结果。
    public void test03(){
        System.out.println("test03:-------------");
        CompletableFuture<String> stage1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("线程:"+Thread.currentThread().getName()+"运行中");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "abc";
        });
        CompletableFuture<String> stage2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("线程:"+Thread.currentThread().getName()+"运行中");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "def";
        });
        System.out.println(Thread.currentThread().getName()+"线程运行中");
        String join = stage1.applyToEither(stage2,res -> {
            return res;
        }).join();
        System.out.println(join);
        System.out.println("test03结束");
    }

    /**
     * 类型二： 消费型，拿到上一步结果，直接消费掉
     */
    // 消费型： 测试thenAcceptBoth，无返回值。
    public void test04(){
        System.out.println("test04:-------------");
        CompletableFuture<String> stage = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("线程:"+Thread.currentThread().getName()+"运行中");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "abc";
        });
        System.out.println(Thread.currentThread().getName()+"线程运行中");
        // join方法同步阻塞，等待异步方法完成
        stage.thenAccept(res -> {
            System.out.println(res);
        });
        System.out.println("test04结束");
    }

    // 消费型： 测试thenAcceptBoth，同时等待2个异步线程结果。
    public void test05(){
        System.out.println("test05:-------------");
        CompletableFuture<String> stage1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("线程:"+Thread.currentThread().getName()+"运行中");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "abc";
        });
        CompletableFuture<String> stage2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("线程:"+Thread.currentThread().getName()+"运行中");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "def";
        });
        System.out.println(Thread.currentThread().getName()+"线程运行中");
        stage1.thenAcceptBoth(stage2,(res1,res2)-> {
            System.out.println(res1 + res2);
        }).join();
        System.out.println("test05结束");
    }

    // 消费型： 测试acceptEither，同时等待2个异步线程结果。
    public void test06(){
        System.out.println("test06:-------------");
        CompletableFuture<String> stage1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("线程:"+Thread.currentThread().getName()+"运行中");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "abc";
        });
        CompletableFuture<String> stage2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("线程:"+Thread.currentThread().getName()+"运行中");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "def";
        });
        System.out.println(Thread.currentThread().getName()+"线程运行中");
        stage1.acceptEither(stage2,res -> {
            System.out.println(res);
        }).join();
        System.out.println("test06结束");
    }

    /**
     * 类型三： 不产出不消费型，只要两个阶段执行成功，就执行
     */
    // 只要求依赖的阶段正常完成的不消耗也不产出型: 测试依赖单个阶段，thenRun
    public void test07(){
        System.out.println("test07:-------------");
        CompletableFuture<String> stage = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("线程:"+Thread.currentThread().getName()+"运行中");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "abc";
        });
        System.out.println(Thread.currentThread().getName()+"线程运行中");
        // join方法同步阻塞，等待异步方法完成
        // 此处需要注意，如果不加join，会造成主线程直接执行完关闭，子线程还没有来得及执行就关闭了JVM
        stage.thenRun(()-> {
            try {
                Thread.sleep(1000);
                System.out.println("thenRun");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).join();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("test07结束");
    }

    // 两个阶段正常完成，拿不到结果，不消耗也不产出型：runAfterBoth
    public void test08(){
        System.out.println("test08:-------------");
        CompletableFuture<String> stage1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("线程:"+Thread.currentThread().getName()+"运行中");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "abc";
        });
        CompletableFuture<String> stage2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("线程:"+Thread.currentThread().getName()+"运行中");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "def";
        });
        System.out.println(Thread.currentThread().getName()+"线程运行中");
        stage1.runAfterBoth(stage2,()-> {
            System.out.println("runAfterBoth");
        }).join();
        System.out.println("test08结束");
    }

    // 两个阶段有一个提前正常完成，拿不到结果，不消耗也不产出型：runAfterEither
    public void test09(){
        System.out.println("test09:-------------");
        CompletableFuture<String> stage1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("线程:"+Thread.currentThread().getName()+"运行中");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "abc";
        });
        CompletableFuture<String> stage2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("线程:"+Thread.currentThread().getName()+"运行中");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "def";
        });
        System.out.println(Thread.currentThread().getName()+"线程运行中");
        stage1.runAfterEither(stage2,()-> {
            System.out.println("runAfterEither");
        }).join();
        System.out.println("test09结束");
    }

    /**
     * 以上 三种类型，执行的前提是，之前的阶段都执行成功才能进入方法，否则就进入下面的异常方法
     * 异常方法也常用作最后的处理阶段
     */
    /**
     * 类型四： 带异常处理，消费型。whenComplete
     * 无论是否发生异常都会走,方法中需要判断e是否为null
     */
    public void test10(){
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (1 == 1) {
                throw new RuntimeException("测试一下异常情况");
            }

            return "hello ";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("return world...");  //会执行
            return "world";
        }), (s1, s2) -> {
            String s = s1 + " " + s2;   //并不会执行
            System.out.println("combine result :"+s); //并不会执行
            return s;
        }).whenComplete((s, t) -> {
            System.out.println("current result is :" +s);
            if(t != null){
                System.out.println("阶段执行过程中存在异常：");
                t.printStackTrace();
            }
        }).join();

        System.out.println("final result:"+result); //并不会执行
    }

    /**
     * 类型五： 带异常处理，产出型。handle
     * 无论是否发生异常都会走,方法中需要判断e是否为null
     * 可以对最终结果进行补偿或转换
     */
    public void test11() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //出现异常
            if (1 == 3) {
                throw new RuntimeException("测试一下异常情况");
            }
            return "Tom";
        }).handle((s, t) -> {
            if (t != null) { //出现异常了
                return "John";
            }
            return s; //这里也可以对正常结果进行转换
        }).join();
        System.out.println(result);
    }

    /**
     * 类型六： 出现异常产出型：exceptionally
     * 只有出现异常才会进入此逻辑
     * 可以对最终结果进行补偿或转换
     */
    public void test12() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (1 == 1) {
                throw new RuntimeException("测试一下异常情况");
            }
            return "s1";
        }).exceptionally(e -> {
            e.printStackTrace(); //e肯定不会null
            return "hello world"; //补偿返回
        }).join();
        System.out.println(result); //打印hello world
    }
}
