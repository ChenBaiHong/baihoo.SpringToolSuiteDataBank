package com.baihoo.blog.vo;
/**
 * 前臺頁面，相應返回的對象類
 * 
 * @author Administrator
 *
 */
public class Response {
		/**
		 * @param success 	判斷相應是否是成功的處理
		 * @param message	返回處理成功，還是失敗的提示信息
		 * @param body			對查詢數據的封裝對象，或其他的封裝對象
		 * 
		 */
	
		private boolean success;
		private String message;
		private Object body;
		public boolean isSuccess() {
			return success;
		}
		public void setSuccess(boolean success) {
			this.success = success;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public Object getBody() {
			return body;
		}
		public void setBody(Object body) {
			this.body = body;
		}
		public Response(boolean success, String message) {
			super();
			this.success = success;
			this.message = message;
		}
		public Response(boolean success, String message, Object body) {
			super();
			this.success = success;
			this.message = message;
			this.body = body;
		}
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		
}
