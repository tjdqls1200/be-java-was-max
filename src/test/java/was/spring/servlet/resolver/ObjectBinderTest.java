package was.spring.servlet.resolver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ObjectBinderTest {
    @DisplayName("클래스 타입과 arguments를 통해 객체를 생성한다.")
    @Test
    void objectBindingSuccessTest() throws ReflectiveOperationException {
        final ObjectBinder objectBinder = new ObjectBinder();
        final Map<String, String> args = Map.of(
                "name", "sungbin",
                "age", "30");

        final TestObjectForSuccess object = (TestObjectForSuccess) objectBinder.mapToBind(TestObjectForSuccess.class, args);

        assertThat(object.getName()).isEqualTo("sungbin");
        assertThat(object.getAge()).isEqualTo(30);
    }

    @DisplayName("기본 생성자가 없으면 NoSuchMethodException 예외가 발생한다.")
    @Test
    void objectBindingFailTest() {
        final ObjectBinder objectBinder = new ObjectBinder();
        final Map<String, String> args = Map.of(
                "name", "sungbin",
                "age", "30");

        assertThatThrownBy(() -> objectBinder.mapToBind(TestObjectForFail.class, args)).isInstanceOf(NoSuchMethodException.class);
    }

    static class TestObjectForSuccess {
        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    static class TestObjectForFail {
        private String name;
        private int age;

        public TestObjectForFail(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}