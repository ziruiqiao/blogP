import service from "@/request/index.js";

export function getAllArticles(params) {
  return service({
    method: "post",
    url: "/articles",
    data: params,
  });
}

export function getHotArticles() {
  return service({
    url: "/articles/hot",
    method: "post",
  });
}

export function getNewArticles() {
  return service({
    url: "/articles/new",
    method: "post",
  });
}

export function getArchives() {
  return service({
    url: "/articles/listArchives",
    method: "post",
  });
}

export function viewArticle(id) {
  return service({
    url: `/articles/view/${id}`,
    method: 'post'
  })
}
export function getArticleById(id) {
  return service({
    url: `/articles/${id}`,
    method: 'post'
  })
}