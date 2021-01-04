package crud.noticeboard.controller;

import crud.noticeboard.domain.Comment;
import crud.noticeboard.domain.File;
import crud.noticeboard.domain.Member;
import crud.noticeboard.domain.Post;
import crud.noticeboard.dto.*;
import crud.noticeboard.repository.CommentRepository;
import crud.noticeboard.repository.FileRepository;
import crud.noticeboard.repository.MemberRepository;
import crud.noticeboard.repository.PostRepository;
import crud.noticeboard.service.FileService;
import crud.noticeboard.service.MemberService;
import crud.noticeboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final FileRepository fileRepository;
    private final FileService fileService;

    //글목록 페이지 매핑
    @GetMapping("/postList")
    public String post(Model model, @PageableDefault(size = 5, sort = "postDate", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Post> posts = postRepository.findAll(pageable);
        model.addAttribute("posts", posts);

        return "/post/postList";
    }

    //글쓰기 페이지 매핑
    @GetMapping("/posts/new")
    public String postWrite(Model model){
        model.addAttribute("PostCreateDto", new PostCreateDto());
        return "/post/createPost";
    }

    //글 생성
    @PostMapping("/posts/new")
    public String createPost(@ModelAttribute("PostCreateDto") @Valid PostCreateDto postCreateDto, BindingResult result,
                             @RequestParam("file") MultipartFile files){

        // 제목, 내용 비어있는지 유효성 검사
        if(result.hasErrors()){
            return "/post/createPost";
        }

        try {
            String origFilename = files.getOriginalFilename();
            String filename = origFilename;

            // 실행되는 위치 즉 프로젝트 폴더에 files 폴더에 파일 저장됨
            String savePath = System.getProperty("user.dir") + "\\files";

            //파일 저장되는 폴더 없으면 생성
            if (!new java.io.File(savePath).exists()) {
                try{
                    new java.io.File(savePath).mkdir();
                }
                catch(Exception e){
                    e.getStackTrace();
                }
            }
            String filePath = savePath + "\\" + filename;
            files.transferTo(new java.io.File(filePath));

            FileDto fileDto = new FileDto();
            fileDto.setOriginFilename(origFilename);
            fileDto.setFilename(filename);
            fileDto.setFilePath(filePath);

            //파일 DB에 저장
            fileService.saveFile(fileDto);

            //DB에서 모든 파일 가져와서 Post 생성할 때 넘겨줌
            List<File> findFiles = fileRepository.findAll();

            //글 생성
            postService.createPostWithFile(postCreateDto.getTitle(), postCreateDto.getContent(), findFiles);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return "redirect:/postList";
    }

    //글 읽기 페이지 매핑
    @GetMapping("/post/{postId}/read")
    public String readPost(@PathVariable("postId") Long postId, Model model,
                           @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){

        //글 조회
        Post findPost = postRepository.findByIdCustom(postId);

        //조회수 증가
        postService.addCount(findPost);

        //해당글에대한 댓글들 조회
        Page<Comment> comments = commentRepository.findByComment(postId, pageable);

        //파일 조회
        List<File> files = fileRepository.findAll();

        model.addAttribute("post", findPost);
        model.addAttribute("commentDto", new CommentDto());
        model.addAttribute("comments", comments);
        model.addAttribute("files", files);
        return "/post/readPost";
    }

    //글 수정 화면 매핑
    @GetMapping("/post/{postId}/update")
    public String updatePostForm(@PathVariable("postId") Long postId, Model model){
        Post findPost = postRepository.findByIdCustom(postId);

        //로그인한 사용자의 이름
        Member loginMember = memberService.findLoginMember();

        //로그인한 사용자와 글을쓴 사용자가 일치하는지
        if( !((loginMember.getName()).equals(findPost.getMember().getName())) ){
            return "/error/errorUpdate";
        }

        PostCreateDto postCreateDto = new PostCreateDto();
        postCreateDto.setTitle(findPost.getTitle());
        postCreateDto.setContent(findPost.getContent());

        model.addAttribute("PostCreateDto", postCreateDto);
        model.addAttribute("postId", postId);
        return "/post/updatePost";
    }

    //글 수정
    @PostMapping("/post/{postId}/update")
    public String updatePost(@ModelAttribute("PostCreateDto") @Valid PostCreateDto postCreateDto,
                             BindingResult result, @PathVariable("postId") Long postId){
        if(result.hasErrors()){
            return "/post/updatePost";
        }

        postService.updatePost(postId, postCreateDto);

        return "redirect:/post/{postId}/read";

    }

    //글 삭제
    @PostMapping("/post/{postId}/remove")
    public String removePost(@PathVariable("postId") Long postId){

        Post findPost = postRepository.findByIdCustom(postId);

        Member loginMember = memberService.findLoginMember();

        if( !((loginMember.getName()).equals(findPost.getMember().getName())) ){
            return "/error/errorDelete";
        }

        postService.removePost(postId);
        return "redirect:/postList";
    }

    //검색
    @GetMapping("/posts/search")
    public String search(@RequestParam("searchOption") String searchOption, PostSearchCondition condition,
                         @PageableDefault(size = 5, sort = "id") Pageable pageable, Model model){

        if(searchOption.equals("title")){
            condition.setTitle(condition.getSearchWord());

            Page<PostMemberSearchDto> results = postRepository.search(condition, pageable);
            model.addAttribute("posts", results);
        }
        else if(searchOption.equals("name")){
            condition.setName(condition.getSearchWord());

            Page<PostMemberSearchDto> results = postRepository.search(condition, pageable);
            model.addAttribute("posts", results);
        }

        return "/post/postListSearch";
    }
}
