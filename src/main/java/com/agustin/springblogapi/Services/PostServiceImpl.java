package com.agustin.springblogapi.Services;

import com.agustin.springblogapi.DTO.PostDTO;
import com.agustin.springblogapi.Entities.Post;
import com.agustin.springblogapi.Exceptions.ResourceNotFoundException;
import com.agustin.springblogapi.Repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    // implementación del servicio para crear un nuevo post
    @Override
    public PostDTO createPost(PostDTO postDTO) {
        // convertimos el DTO a una entidad para guardarlo en la base de datos
        Post post = entityMapper(postDTO);

        // guardamos el objeto en la base de datos
        Post newPost = postRepository.save(post);

        // convertimos esa entidad a DTO para responder el JSON del nuevo registro
        PostDTO postResponse = DTOMapper(newPost);

        // Devolvemos la respuesta
        return postResponse;
    }

    // implementación del servicio para obtener todos los posts
    @Override
    public List<PostDTO> getAllPosts() {

        List<Post> posts = postRepository.findAll();

        return posts.stream().map(post -> DTOMapper(post)).collect(Collectors.toList());
    }

    // implementación del servicio para obtener un post mediante su id
    @Override
    public PostDTO getPostById(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        return DTOMapper(post);
    }

    // implementacion del servicio para actualizar un post mediante la obtencion de su id
    @Override
    public PostDTO updatePostService(PostDTO postDTO, long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());

        Post updatedPost = postRepository.save(post);

        return DTOMapper(updatedPost);

    }

    // implementacion del servicio para eliminar un post mediante su id
    @Override
    public void deletePostService(long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        postRepository.delete(post);
    }

    // este método convierte la entidad a DTO
    public PostDTO DTOMapper(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setDescription(post.getDescription());
        postDTO.setContent(post.getContent());

        return postDTO;
    }

    // este método convierte de DTO a entidad
    public Post entityMapper(PostDTO postDTO) {
        Post post = new Post();

        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());

        return post;
    }
}
