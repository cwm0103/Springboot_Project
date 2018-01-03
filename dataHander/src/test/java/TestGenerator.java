import org.junit.Before;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
public class TestGenerator {
    private File configFile;

    @Before
    public void before() {
        //读取mybatis参数
        //configFile = new File("/SVNNew/Development/Product/HMA/源代码/hmaplant-v1.2/core/src/main/resources/generatorConfig.xml");
//        configFile = new File("/svn/dataHander/src/main/resources/config/generatorConfig.xml");
        configFile = new File("D:/ServerProject/dataHander/src/main/resources/config/generatorConfig.xml");

      //  configFile = new File("/泛能站/fannengwork/datav1.0/src/main/resources/config/generatorConfig.xml");
      // configFile = new File("/泛能站/fanneng1.2/src/main/resources/config/generatorConfig.xml");
        //        configFile = new File("/Users/zhangsiyuan/Documents/MybatisFun/Mybatis-Chapter9-GeneratorPlugin/src/main/resources/mybatisConfig.xml");
    }

    @Test
    public void test() throws Exception{
     /*   List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);*/
    }
}
