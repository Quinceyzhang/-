package com.luckyframe.project.system.post.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.luckyframe.common.constant.UserConstants;
import com.luckyframe.common.exception.BusinessException;
import com.luckyframe.common.support.Convert;
import com.luckyframe.common.utils.StringUtils;
import com.luckyframe.common.utils.security.ShiroUtils;
import com.luckyframe.project.system.post.domain.Post;
import com.luckyframe.project.system.post.domain.PostParams;
import com.luckyframe.project.system.post.domain.PostParamsList;
import com.luckyframe.project.system.post.mapper.FileIndexMapper;
import com.luckyframe.project.system.post.mapper.PostMapper;
import com.luckyframe.project.system.user.mapper.UserPostMapper;
import com.luckyframe.project.testmanagmt.projectCaseParams.domain.ProjectCaseParams;

/**
 * 岗位信息 服务层处理
 * 
 * @author ruoyi
 */
@Service
public class PostServiceImpl implements IPostService
{
    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserPostMapper userPostMapper;
    
    @Resource
    private FileIndexService fileIndexService;

    /**
     * 查询岗位信息集合
     * 
     * @param post 岗位信息
     * @return 岗位信息集合
     */
    @Override
    public List<Post> selectPostList(Post post)
    {
        return postMapper.selectPostList(post);
    }

    /**
     * 查询所有岗位
     * 
     * @return 岗位列表
     */
    @Override
    public List<Post> selectPostAll()
    {
        return postMapper.selectPostAll();
    }

    /**
     * 根据用户ID查询岗位
     * 
     * @param userId 用户ID
     * @return 岗位列表
     */
    @Override
    public List<Post> selectPostsByUserId(Long userId)
    {
        List<Post> userPosts = postMapper.selectPostsByUserId(userId);
        List<Post> posts = postMapper.selectPostAll();
        for (Post post : posts)
        {
            for (Post userRole : userPosts)
            {
                if (post.getPostId().longValue() == userRole.getPostId().longValue())
                {
                    post.setFlag(true);
                    break;
                }
            }
        }
        return posts;
    }

    /**
     * 通过岗位ID查询岗位信息
     * 
     * @param postId 岗位ID
     * @return 角色对象信息
     */
    @Override
    public Post selectPostById(Long postId)
    {
        return postMapper.selectPostById(postId);
    }

    /**
     * 批量删除岗位信息
     * 
     * @param ids 需要删除的数据ID
     */
    @Override
    public int deletePostByIds(String ids) throws BusinessException
    {
        Long[] postIds = Convert.toLongArray(ids);
        for (Long postId : postIds)
        {
           Post post = selectPostById(postId);
           if(post != null){
        	   String fileId = post.getPostSort();
        	   if( fileId!= null && fileId.matches("\\d+")){
        		   this.fileIndexService.deleteFile(Integer.valueOf(fileId));
        	   }
           }
           /* if (countUserPostById(postId) > 0)
            {
                throw new BusinessException(String.format("%1$s已分配,不能删除", post.getPostName()));
            }*/
        	postMapper.deletePostParams(postId);
        }
        return postMapper.deletePostByIds(postIds);
    }

    /**
     * 新增保存岗位信息
     * 
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public int insertPost(Post post)
    {
        post.setCreateBy(ShiroUtils.getLoginName());
        return postMapper.insertPost(post);
    }

    /**
     * 修改保存岗位信息
     * 
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public int updatePost(Post post)
    {
        post.setUpdateBy(ShiroUtils.getLoginName());
        return postMapper.updatePost(post);
    }

    /**
     * 通过岗位ID查询岗位使用数量
     * 
     * @param postId 岗位ID
     * @return 结果
     */
    @Override
    public int countUserPostById(Long postId)
    {
        return userPostMapper.countUserPostById(postId);
    }

    /**
     * 校验岗位名称是否唯一
     * 
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public String checkPostNameUnique(Post post)
    {
        long postId = StringUtils.isNull(post.getPostId()) ? -1L : post.getPostId();
        Post info = postMapper.checkPostNameUnique(post.getPostName());
        if (StringUtils.isNotNull(info) && info.getPostId() != postId)
        {
            return UserConstants.POST_NAME_NOT_UNIQUE;
        }
        return UserConstants.POST_NAME_UNIQUE;
    }

    /**
     * 校验岗位编码是否唯一
     * 
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public String checkPostCodeUnique(Post post)
    {
        long postId = StringUtils.isNull(post.getPostId()) ? -1L : post.getPostId();
        Post info = postMapper.checkPostCodeUnique(post.getPostCode());
        if (StringUtils.isNotNull(info) && info.getPostId() != postId)
        {
            return UserConstants.POST_CODE_NOT_UNIQUE;
        }
        return UserConstants.POST_CODE_UNIQUE;
    }

	@Override
	public List<PostParams> selectPostParamsList(Long postId) {
		return postMapper.selectPostParamsList(postId);
	}

	@Override
	public void deletePostParams(long postId) {
		postMapper.deletePostParams(postId);
	}

	@Override
	public int insertPostParams(PostParams params) {
	    postMapper.insertPostParams(params);
		return 1;
	}

	@Override
	public List<PostParamsList> selectAllPostList(Integer actionId) {
		return postMapper.selectAllPostList(actionId);
	}

	@Override
	public List<ProjectCaseParams> selectParamsListForPost(Long postId) {
		return postMapper.selectParamsListForPost(postId);
	}

	@Override
	public int isOccupy(String ids) {
		String[] postIds=Convert.toStrArray(ids);
		int occupy =0;
		for(String postId:postIds){
			occupy = occupy + Integer.parseInt(postMapper.postIsOccupy(postId).get("count"));
		}
		return occupy;
	}
}
