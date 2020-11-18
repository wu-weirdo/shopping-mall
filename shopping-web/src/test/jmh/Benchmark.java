package jmh;

import com.edaochina.shopping.domain.base.page.Pages;
import com.edaochina.shopping.domain.dto.goods.QueryAppGoodsDTO;
import com.edaochina.shopping.web.WebApplication;
import com.edaochina.shopping.web.goods.AppGoodsController;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * Benchmark
 *
 * @author wangpenglei
 * @since 2019/11/27 13:54
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class Benchmark {

    private ConfigurableApplicationContext context;
    private AppGoodsController controller;

    public static void main(String[] args) throws Exception {
        // 使用一个单独进程执行测试，执行5遍warmup，然后执行5遍测试
        Options opt = new OptionsBuilder().include(Benchmark.class.getSimpleName()).forks(1).warmupIterations(5)
                .measurementIterations(5).build();
        new Runner(opt).run();
    }

    @Setup
    public void init() {
        context = SpringApplication.run(WebApplication.class);
        this.controller = context.getBean(AppGoodsController.class);
    }

    @TearDown
    public void down() {
        context.close();
    }

    @org.openjdk.jmh.annotations.Benchmark
    public void testGetGoodsList() {
        QueryAppGoodsDTO dto = new QueryAppGoodsDTO();
        dto.setPages(new Pages(1, 10));
        controller.getGoodsList(dto);
    }

}
