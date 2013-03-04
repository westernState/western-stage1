package microblog4

import org.springframework.dao.DataIntegrityViolationException

class MicroPostController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [microPostInstanceList: MicroPost.list(params), microPostInstanceTotal: MicroPost.count()]
    }
    
    def create() {
        [microPostInstance: new MicroPost(params)]
    }

    def save() {
        def microPostInstance = new MicroPost(params)
        microPostInstance.postDate = new Date()
        if (!microPostInstance.save(flush: true)) {
            render(view: "create", model: [microPostInstance: microPostInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'microPost.label', default: 'MicroPost'), microPostInstance.id])
        redirect(action: "show", id: microPostInstance.id)
    }

    def show(Long id) {
        def microPostInstance = MicroPost.get(id)
        if (!microPostInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'microPost.label', default: 'MicroPost'), id])
            redirect(action: "list")
            return
        }

        [microPostInstance: microPostInstance]
    }

    def edit(Long id) {
        def microPostInstance = MicroPost.get(id)
        if (!microPostInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'microPost.label', default: 'MicroPost'), id])
            redirect(action: "list")
            return
        }

        [microPostInstance: microPostInstance]
    }

    def update(Long id, Long version) {
        def microPostInstance = MicroPost.get(id)
        if (!microPostInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'microPost.label', default: 'MicroPost'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (microPostInstance.version > version) {
                microPostInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'microPost.label', default: 'MicroPost')] as Object[],
                          "Another user has updated this MicroPost while you were editing")
                render(view: "edit", model: [microPostInstance: microPostInstance])
                return
            }
        }

        microPostInstance.properties = params

        if (!microPostInstance.save(flush: true)) {
            render(view: "edit", model: [microPostInstance: microPostInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'microPost.label', default: 'MicroPost'), microPostInstance.id])
        redirect(action: "show", id: microPostInstance.id)
    }

    def delete(Long id) {
        def microPostInstance = MicroPost.get(id)
        if (!microPostInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'microPost.label', default: 'MicroPost'), id])
            redirect(action: "list")
            return
        }

        try {
            microPostInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'microPost.label', default: 'MicroPost'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'microPost.label', default: 'MicroPost'), id])
            redirect(action: "show", id: id)
        }
    }
}
