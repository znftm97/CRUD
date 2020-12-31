package crud.noticeboard.controller;

import crud.noticeboard.domain.Post;
import crud.noticeboard.dto.PostCreateDto;
import crud.noticeboard.repository.PostRepository;
import crud.noticeboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;

    //글목록 페이지 매핑
    @GetMapping("/postList")
    public String post(Model model, @PageableDefault(size = 2, sort = "postDate", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Post> posts = postRepository.findAll(pageable);
        model.addAttribute("posts", posts);

        return "/postList";
    }

    //글쓰기 페이지 매핑
    @GetMapping("/posts/new")
    public String postWrite(Model model){
        model.addAttribute("PostCreateDto", new PostCreateDto());
        return "/createPost";
    }

    //글 생성
    @PostMapping("/posts/new")
    public String createPost(@ModelAttribute("PostCreateDto") @Valid PostCreateDto postCreateDto, BindingResult result){

        if(result.hasErrors()){
            return "/createPost";
        }

        postService.createPost(postCreateDto.getTitle(), postCreateDto.getContent());

        return "redirect:/postList";
    }

    //글 읽기 페이지 매핑
    @GetMapping("/post/{postId}/read")
    public String readPost(@PathVariable("postId") Long postId, Model model){
        Post findPost = postRepository.findByIdCustom(postId);
        postService.addCount(findPost);

        model.addAttribute("post", findPost);
        return "/readPost";

    }

    //글 수정 화면 매핑
    @GetMapping("/post/{postId}/update")
    public String updatePostForm(@PathVariable("postId") Long postId, Model model){
        Post findPost = postRepository.findByIdCustom(postId);

        PostCreateDto postCreateDto = new PostCreateDto();
        postCreateDto.setTitle(findPost.getTitle());
        postCreateDto.setContent(findPost.getContent());

        model.addAttribute("PostCreateDto", postCreateDto);
        model.addAttribute("postId", postId);
        return "/updatePost";
    }

    //글 수정
    @PostMapping("/post/{postId}/update")
    public String updatePost(@ModelAttribute("PostCreateDto") @Valid PostCreateDto postCreateDto, BindingResult result, @PathVariable("postId") Long postId){
        if(result.hasErrors()){
            return "/updatePost";
        }

        postService.updatePost(postId, postCreateDto);

        return "redirect:/postList";

    }

    //글 삭제
    @PostMapping("/post/{postId}/remove")
    public String removePost(@PathVariable("postId") Long postId){
        postService.removePost(postId);
        return "redirect:/postList";
    }
}
