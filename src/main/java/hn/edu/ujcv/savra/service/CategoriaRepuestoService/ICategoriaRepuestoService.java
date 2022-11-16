package hn.edu.ujcv.savra.service.CategoriaRepuestoService;


import hn.edu.ujcv.savra.entity.CategoriaRepuesto;
import hn.edu.ujcv.savra.exceptions.BusinessException;
import hn.edu.ujcv.savra.exceptions.NotFoundException;

import java.util.List;

public interface ICategoriaRepuestoService {
    CategoriaRepuesto       saveCategoriaRepuesto(CategoriaRepuesto categoriaRepuesto) throws BusinessException;
    List<CategoriaRepuesto> saveCategoriasRepuesto(List<CategoriaRepuesto> categoriasRepuesto) throws BusinessException;
    List<CategoriaRepuesto> getCategoriasRepuesto() throws BusinessException;
    CategoriaRepuesto       getCategoriaRepuestoById(long id) throws BusinessException, NotFoundException;
    CategoriaRepuesto       getCategoriaRepuestoByNombre(String nombre) throws BusinessException, NotFoundException;
    void                    deleteCategoriaRepuesto(long id) throws BusinessException, NotFoundException;
    CategoriaRepuesto       updateCategoriaRepuesto(CategoriaRepuesto categoriaRepuesto) throws BusinessException, NotFoundException;
}
