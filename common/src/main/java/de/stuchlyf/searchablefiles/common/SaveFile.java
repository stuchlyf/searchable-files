package de.stuchlyf.searchablefiles.common;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public record SaveFile(
        String path,
        String mediaType,
        byte[] contents
) implements Serializable {

    @Serial
    private static final long serialVersionUID = -2705033372750310803L;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaveFile saveFile = (SaveFile) o;
        return Objects.equals(path, saveFile.path) && Arrays.equals(contents, saveFile.contents);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(path);
        result = 31 * result + Arrays.hashCode(contents);
        return result;
    }

    @Override
    public String toString() {
        return "SaveFile{" +
                "path='" + path + '\'' +
                ", contents=" + Arrays.toString(contents) +
                '}';
    }
}
