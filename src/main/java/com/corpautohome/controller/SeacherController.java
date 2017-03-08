package com.corpautohome.controller;

import com.alibaba.fastjson.JSON;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by gaonq on 2017/3/4.
 */
@Controller
public class SeacherController {

    @RequestMapping("/search")
    public String Search(HttpServletRequest request, HttpServletResponse response) {
        return "search";
    }
    @RequestMapping(value = "/doSearch",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public void doSearch(@RequestParam(value = "keyword") String keywordSearcher,@RequestParam(value = "searchtype") String searchtype, HttpServletRequest request, HttpServletResponse response) throws IOException, SolrServerException {
        String url = "http://localhost:8050/solr/oatest";

        HttpSolrClient server = new HttpSolrClient(url);
       // String keyword = request.getParameter("keyword");

        System.out.println("searchtype："+searchtype);
//        if(searchtype.equals("database"))
//        {
//            keywordSearcher = "exception:\""+keywordSearcher+"\"";
//        }
//        else if(searchtype.equals("file"))
//        {
//            keywordSearcher = "attr_content:\""+keywordSearcher+"\"";
//        }
//        keywordSearcher = "text_ik:\""+keywordSearcher+"\"";
        keywordSearcher = "text_ik:"+keywordSearcher+"";
        //keywordSearcher = "exception:\""+keywordSearcher+"\" or attr_content:\""+keywordSearcher+"\"";
        System.out.println("搜索的关键词："+keywordSearcher);
        //定义查询内容 * 代表查询所有    这个是基于结果集
        SolrQuery query = new SolrQuery(keywordSearcher); //定义查询内容
        query.setStart(0);//起始页
        query.setRows(100);//每页显示数量

        //设置高亮
        query.addHighlightField("attr_stream_name");
        query.setHighlight(true);
        query.setHighlightSimplePre("<span style=\"color:red\">");
        query.setHighlightSimplePost("</span>");

        QueryResponse rsp = server.query( query );
        SolrDocumentList results = rsp.getResults();
       // System.out.println(results.getNumFound());//查询总条数
        String jsonResult = getJSONString(request,results,keywordSearcher,rsp);
        //System.out.println(jsonResult);
         renderData(response,jsonResult);
    }
    private String getJSONString(HttpServletRequest request,SolrDocumentList solrDocuments,String keyword,QueryResponse rsp ) {
        //故意构造一个数组，使返回的数据为json数组，数据更复杂些
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        Map<String, Object> map1 = new HashMap<String, Object>(10);
        //可以获得ajax请求中的参数
        String resultCount =String.valueOf(solrDocuments.getNumFound());
        map1.put("keyword",keyword);
        Map<String, Object> map3 = new HashMap<String, Object>(10);
        map3.put("resultCount", resultCount);
        Map<String, Object> map4 = new HashMap<String, Object>(10);
        map4.put("qtime",  rsp.getQTime());
        System.out.println("搜索的关键词耗时："+rsp.getQTime());

        datas.add(map1);
        datas.add(map3);
        datas.add(map4);
        Map<String, Object> map2 ;
        //获取高亮结果
        Map<String, Map<String, List<String>>> hightlight = rsp.getHighlighting();
        for(SolrDocument doc:solrDocuments){
            map2 = new HashMap<String, Object>();
            map2.put("createTime",doc.getFieldValue("createTime"));
            map2.put("level",doc.getFieldValue("level"));
            map2.put("message",doc.getFieldValue("message"));
            map2.put("errordate",doc.getFieldValue("errordate"));
            map2.put("exception", doc.getFieldValue("exception"));

            String hightlightName = "";
            if(hightlight!=null){
                //获取高亮结果

                if(hightlight.get(doc.get("id").toString())!=null && hightlight.get(doc.get("id").toString()).get("attr_stream_name")!=null){
                    hightlightName =hightlight.get(doc.get("id").toString()).
                            get("attr_stream_name").get(0);
                }else{
                    hightlightName = (String) doc.getFieldValue("attr_stream_name");
                }
//                if(hightlight.get(doc.get("id").toString())!=null && hightlight.get(doc.get("id").toString()).get("content")!=null){
//                    String t=hightlight.get(doc.get("id").toString())
//                            .get("content").get(0);
//                    t=t.length()>72 ? t.substring(0, 72) : t;
//                    System.out.println(t);
//                }else{
//                    System.out.println(doc.get("content"));
//                }
            }else
            {
                hightlightName = (String) doc.getFieldValue("attr_stream_name");
            }
            map2.put("attr_stream_name",hightlightName);
            datas.add(map2);
        }
        String jsonResult;
        jsonResult = JSON.toJSONString(datas);
        return jsonResult;
    }
    /**
     * 通过PrintWriter将响应数据写入response，ajax可以接受到这个数据
     *
     * @param response
     * @param data
     */
    private void renderData(HttpServletResponse response, String data) {
        response.setContentType("application/json");
        response.setHeader("Cache-Control","no-cache");
        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter();
            printWriter.print(data);
        } catch (IOException ex) {
        } finally {
            if (null != printWriter) {
                printWriter.flush();
                printWriter.close();
            }
        }
    }
}
