import service from "@/request/index.js"


export function listCategorysDetail() {
	return service({
		  method: 'get',
		  url: '/categories/detail'
		})
}

export function getCategoryDetailById(id) {
	return service({
		method: 'get',
		url: `/categories/detail/${id}`
	  })
}