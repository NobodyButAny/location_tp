package org.example.location_tp;

import com.google.common.base.Strings;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;
import java.util.Objects;

/*
store.yml

{landmark_name}:
    pos: Location
    authors: []
    description: String
 */
public record Landmark(Location pos, String name, List<String> authors, String description) {
    public Landmark {
        Objects.requireNonNull(pos, "Landmark location shouldn't be null!");
        if (Objects.isNull(name) || name == "") throw new IllegalArgumentException("Landmark name is null or empty!");
    }

    public Landmark(Location pos, String name, String author) {
        this(pos, name, List.of(name), null);
    }

    public Landmark(Location pos, String name, List<String> authors) {
        this(pos, name, authors, null);
    }

    public static Landmark load(YamlConfiguration store, String name) {
        var section = store.getConfigurationSection(name);
        if (Objects.isNull(section)) return null;
        var pos = section.getLocation("pos");
        var author = section.getStringList("authors");
        var description = section.getString("description");
        return new Landmark(pos, name, author, description);
    }

    public void save(YamlConfiguration store) {
        var section = store.createSection(this.name);
        section.set("pos", this.pos);
        section.set("authors", this.authors);
        if (!Objects.isNull(description))
            section.set("description", this.description);
    }

    public String chatString() {
        StringBuilder result = new StringBuilder();
        result.append("Локация: ")
                .append("\"" + this.name + "\"")
                .append("\n")
                .append("Позиция: ")
                .append(pos.getBlockX() + " " + pos.getBlockY() + " " + pos.getBlockZ())
                .append("\n")
                .append("- ")
                .append(String.join(", ", this.authors));
        if (!Objects.isNull(description) && !description.isBlank())
            result.append("\n").append(this.description);
        return result.toString();
    }
}
