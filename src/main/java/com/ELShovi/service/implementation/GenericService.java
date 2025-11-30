package com.ELShovi.service.implementation;

import com.ELShovi.repository.IGenericRepository;
import com.ELShovi.service.IGenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Method;
import java.util.List;


public abstract class GenericService<T,ID> implements IGenericService<T,ID> {

    protected abstract IGenericRepository<T,ID> getRepo();

    @Override
    public T save(T entity) throws Exception {
        return getRepo().save(entity);
    }

    @Override
    public T update(T entity, ID id) throws Exception {
        getRepo().findById(id).orElseThrow(()-> new Exception("ID NOT FOUND: " + id));

        //Validación de ID
        // Aquí se podría agregar lógica adicional para validar que el ID del entity coincide con el ID proporcionado
        //entity.setId(id); // Suponiendo que la entidad tiene un método setId    Java Reflection para obtener el nombre de la clase

        Class<?> clazz = entity.getClass();
        String className = clazz.getSimpleName();

        //setId mediante reflexión
        String methodName = "setId" + className; // Asumiendo que el método sigue el patrón setId<ClassName>
        Method setIdMethod = clazz.getMethod(methodName, id.getClass());
        setIdMethod.invoke(entity, id);
        return getRepo().save(entity);
    }

    @Override
    public List<T> findAll() throws Exception {
        return getRepo().findAll();
    }

    @Override
    public T findById(ID id) throws Exception {
        return getRepo().findById(id).orElseThrow(()-> new Exception("ID NOT FOUND: " + id));
    }

    @Override
    public void delete(ID id) throws Exception {
        getRepo().findById(id).orElseThrow(()->new Exception("ID NOT FOUND: " + id));
        getRepo().deleteById(id);
    }

    // Paginación
    @Override
    public Page<T> paginar(int page, int size, String sortBy) throws Exception {
        return getRepo().findAll(PageRequest.of(page,size, Sort.by(sortBy)));
    }
}
