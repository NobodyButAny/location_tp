package org.example.location_tp.util;

/*
пр.1:
/landmark create {name} {authors}
/landmark delete {name}
/landmark tp {name}

пр.2:
/send (love/hate) (nicely/angrily) {player} - варианты парно
/send (booty/boobs) (big/small) {player} - варианты смешанные

Вариант 1(явно дерево):
enum Scope {Linear, Branch}

.add(suggestion: String, ghostly: boolean)
.scopeDown(scope: Scope) или down(...)
.scopeUp() или up()
.build()

пр.1:
new CompleterBuilder().scopeDown(Branch)
    .add("create").scopeDown(Linear).add("name", true).add("authors", true).scopeUp()
    .add("delete").scopeDown(Linear).add("name", true).scopeUp()
    .add("tp").scopeDown(Linear).add("name", true)
    .build()
пр.2:
new CB().down(Branch)
    .add("love").down(Linear).add("nicely").add("player", true).up()
    .add("hate").down(Linear).add("angrily").add("player", true).up()
    .add("booty").down(Branch).add("big").add("small").add("player", true).up()
    .add("boobs").down(Branch).add("big").add("small").add("player", true).up()


Вариант 2(сладкий сахар):

CompleterBuilder == BranchingCompleterBuilder
.add(name: string, scope: LinearCompleterBuilder -> LinearCompleterBuilder)
.params(String ...param, silent: boolean)
.ofValues(String name, values: List<String>, scope: scope: LinearCompleterBuilder -> LinearCompleterBuilder)
.repeatLast()

пр.1:
new CompleterBuilder()
    .add("create", scope -> scope.params("name", true).ofValues("authors", --player_names--).repeatLast())
    .add("delete", scope -> scope.ofValues("landmark", --variable_here--))
    .add("tp", scope -> scope.ofValues("landmark", --landmark_names--))

пр.2:
new CB()
    .ofValues("p_1", List.of("booty", "boobs"),
        scope -> scope.ofValues("p_2", List.of("big", "small")).ofValues("players", --player_names--)
    )
    .add("love", scope -> scope.add("nicely").ofValues("players", --player_names--))
    .add("hate", scope -> scope.add("angrily").ofValues("players", --player_names--))
 */

public interface CompleterBuilder {
}
