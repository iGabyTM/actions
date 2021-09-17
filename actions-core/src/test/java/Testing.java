import me.gabytm.util.actions.actions.Action;
import me.gabytm.util.actions.actions.ActionManager;
import me.gabytm.util.actions.actions.ActionMeta;
import me.gabytm.util.actions.actions.Context;
import me.gabytm.util.actions.placeholders.PlaceholderProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class Testing {

    public static void main(String[] args) {
        final ActionManager actionManager = new ActionManager(null, 100) { };

        final long start = System.currentTimeMillis();
        actionManager.register(Person.class, "example", ExampleAction::new);
        actionManager.registerDefaults(Person.class);
        actionManager.getPlaceholderManager().register(new PersonPlaceholdersProvider());
        actionManager.getComponentParser().registerDefaults(Person.class);

        final List<Action<Person>> actions = actionManager.parse(Person.class, Arrays.asList(
                "[data] test this is the value for 'test'",
                "[example] Hei %person_name% o/ math:{%person_age%/2} $[unknown]",
                "[example] Random long: randomL:{%person_age%,200} | Random double: random:{3,100.25,200.35}"
        ));

        actionManager.run(new Person("Gaby", 19), actions, false);
        System.out.println((System.currentTimeMillis() - start) + "ms");
    }

    public static class Person {

        private final String name;
        private final int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

    }

    public static class PersonPlaceholdersProvider implements PlaceholderProvider<Person> {

        @Override
        public @NotNull Class<Person> getType() {
            return Person.class;
        }

        @Override
        public @NotNull String replace(@NotNull Person person, @NotNull String input) {
            return input
                    .replace("%person_name%", person.getName())
                    .replace("%person_age%", Integer.toString(person.age));
        }

    }

    public static class ExampleAction extends Action<Person> {

        public ExampleAction(@NotNull ActionMeta<Person> meta) {
            super(meta);
        }

        @Override
        public void run(@NotNull Person person, @NotNull final Context<Person> context) {
            System.out.println(getMeta().getParsedData(person, context));
        }

    }

}
