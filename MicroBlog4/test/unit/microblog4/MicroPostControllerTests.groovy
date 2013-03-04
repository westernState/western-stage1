package microblog4



import org.junit.*
import grails.test.mixin.*

@TestFor(MicroPostController)
@Mock(MicroPost)
class MicroPostControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/microPost/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.microPostInstanceList.size() == 0
        assert model.microPostInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.microPostInstance != null
    }

    void testSave() {
        controller.save()

        assert model.microPostInstance != null
        assert view == '/microPost/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/microPost/show/1'
        assert controller.flash.message != null
        assert MicroPost.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/microPost/list'

        populateValidParams(params)
        def microPost = new MicroPost(params)

        assert microPost.save() != null

        params.id = microPost.id

        def model = controller.show()

        assert model.microPostInstance == microPost
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/microPost/list'

        populateValidParams(params)
        def microPost = new MicroPost(params)

        assert microPost.save() != null

        params.id = microPost.id

        def model = controller.edit()

        assert model.microPostInstance == microPost
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/microPost/list'

        response.reset()

        populateValidParams(params)
        def microPost = new MicroPost(params)

        assert microPost.save() != null

        // test invalid parameters in update
        params.id = microPost.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/microPost/edit"
        assert model.microPostInstance != null

        microPost.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/microPost/show/$microPost.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        microPost.clearErrors()

        populateValidParams(params)
        params.id = microPost.id
        params.version = -1
        controller.update()

        assert view == "/microPost/edit"
        assert model.microPostInstance != null
        assert model.microPostInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/microPost/list'

        response.reset()

        populateValidParams(params)
        def microPost = new MicroPost(params)

        assert microPost.save() != null
        assert MicroPost.count() == 1

        params.id = microPost.id

        controller.delete()

        assert MicroPost.count() == 0
        assert MicroPost.get(microPost.id) == null
        assert response.redirectedUrl == '/microPost/list'
    }
}
