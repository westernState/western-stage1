<%@ page import="microblog4.MicroPost" %>



<div class="fieldcontain ${hasErrors(bean: microPostInstance, field: 'content', 'error')} ">
	<label for="content">
		<g:message code="microPost.content.label" default="Content" />
		
	</label>
	<g:textField name="content" maxlength="140" value="${microPostInstance?.content}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: microPostInstance, field: 'user', 'error')} required">
	<label for="user">
		<g:message code="microPost.user.label" default="User" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="user" name="user.id" from="${microblog4.User.list()}" optionKey="id" required="" value="${microPostInstance?.user?.id}" class="many-to-one"/>
</div>

