package com.example.literalura;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public List<Autor> getAutoresVivosEnAnio(Integer year) {
        return autorRepository.findByAnioNacimientoLessThanEqualAndAnioMuerteGreaterThanEqual(year, year);
    }


    public List<Autor> getTodosAutores() {
        return autorRepository.findAll();
    }
}