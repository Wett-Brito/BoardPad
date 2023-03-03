package br.com.boardpadbackend.converters;

public interface Converter <E, D> {
    E dtoToEntity (D dto);
    D entityToDto (E entity);
}
