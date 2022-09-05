import service from "@/request/index.js"

export function getHotTags() {
	return service({
		  method: 'get',
		  url: '/tags/hot'
		})
}

export function listTagsDetail() {
	return service({
		  method: 'get',
		  url: '/tags/detail'
		})
}

export function getTagDetailById(id) {
	return service({
		method: 'get',
		url: `/tags/detail/${id}`
	  })
}