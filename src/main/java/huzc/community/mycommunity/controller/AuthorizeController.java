package huzc.community.mycommunity.controller;

import huzc.community.mycommunity.dto.AccessTokenDTO;
import huzc.community.mycommunity.dto.GithubUser;
import huzc.community.mycommunity.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthorizeController {
    //Autowired与Component，调用GithubProdiver
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;


    //指向返回文件
    @GetMapping("/callback")
    //使用name接收code，为String类型
    //使用name接收state，为String类型
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                            HttpServletRequest request){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
            accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(redirectUri);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        if(user != null){
            //登录成功，写cookie和session
            //将user对象放入session，框架集成，前端有了银行卡
            request.getSession().setAttribute("user",user);
            return "redirect:/";
        }else {
            //登录失败，重新登陆
            return "redirect:/";
        }
    }
}
