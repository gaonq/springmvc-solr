package com.corpautohome.controller;

import com.alibaba.fastjson.JSON;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.util.NamedList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.logging.Level;

/**
 * Created by gaonq on 2017/3/5.
 */
@Controller
public class DocumentController {
    /**
     * 添加索引文档
     * @param request
     * @param response
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public void AddDocument(HttpServletRequest request, HttpServletResponse response) throws IOException, SolrServerException {
        String url = "http://localhost:8050/solr/oatest";
        HttpSolrClient solrClient = new HttpSolrClient(url);
        System.out.println("索引文件成功");
        SolrInputDocument solrInputDocument = new SolrInputDocument();
        solrInputDocument.setField("exception","国庆节");
        solrInputDocument.setField("message","api 国庆节");
        solrInputDocument.setField("level", Level.INFO);
        solrInputDocument.setField("id",java.util.UUID.randomUUID() );


        solrClient.add(solrInputDocument);
        UpdateResponse commit = solrClient.commit(true, true);
        NamedList responseHeader = commit.getResponseHeader();
        System.out.println("索引的返回值："+commit);
        HashMap<String,Object> map1 = new HashMap<>();
        map1.put("status",responseHeader.get("status"));

        //索引本地文件
        String fileName = "C:\\Users\\gaonq\\Desktop\\H3 BPM v10.0 产品白皮书（.NET版本）.pdf";
        ContentStreamUpdateRequest up = new ContentStreamUpdateRequest("/update/extract");
        String contentType="application/pdf";
        up.addFile(new File(fileName), contentType);
        up.setParam("literal.id", String.valueOf(java.util.UUID.randomUUID()));
        up.setParam("uprefix", "attr_");
        up.setParam("fmap.content", "attr_content");
        up.setParam("stream.file", fileName);
        up.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);
        solrClient.request(up);

        //索引http硬盘文件
        String fileNameUrl = "C:\\Users\\gaonq\\Desktop\\H3 BPM v10.0 产品白皮书（.NET版本）.pdf";
        ContentStreamUpdateRequest upHttp = new ContentStreamUpdateRequest("/update/extract");
        upHttp.setParam("stream.url", fileName);
        upHttp.setParam("literal.id", String.valueOf(java.util.UUID.randomUUID()));
        upHttp.setParam("uprefix", "attr_");
        upHttp.setParam("fmap.content", "attr_content");
        upHttp.setParam("stream.file", fileName);
        upHttp.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);
        solrClient.request(upHttp);

        renderData(response, JSON.toJSONString(map1));
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
