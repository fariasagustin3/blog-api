package com.agustin.springblogapi.Services;

import com.agustin.springblogapi.DTO.PostDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {

    // crear un nuevo registro por medio del DTO
    public PostDTO createPost(PostDTO postDTO);

    // listar los registros de la base de datos por medio del DTO
    public List<PostDTO> getAllPosts();

    // obtener un post por id
    public PostDTO getPostById(long id);

    // actualizar un post existente
    public PostDTO updatePostService(PostDTO postDTO, long id);

    // eliminar un post existente
    public void deletePostService(long id);
}
