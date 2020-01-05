package huzc.community.mycommunity.controller;

import huzc.community.mycommunity.dto.AccessTokenDTO;
import huzc.community.mycommunity.dto.GithubUser;
import huzc.community.mycommunity.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    //Autowired与Component，调用GithubProdiver
    @Autowired
    private GithubProvider githubProvider;

    //指向返回文件
    @GetMapping("/callback")
    //使用name接收code，为String类型
    //使用name接收state，为String类型
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("eb92e00b211c84362136");
        accessTokenDTO.setClient_secret("88fff532432284514bae361c5a7c7cb7cb028d75");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri("http://localhost:8887/callback");
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName());
        //登录成功后返回index页面
        return "index";
    }
}
