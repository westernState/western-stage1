package microblog4


class User {
    
     
    
    String name
    String email
    SortedSet posts
    static hasMany = {posts: Micropost}
  
    static constraints = {
       email matches: "[a-z A-Z 1-9]+.[a-z A-Z]+.[a-z A-Z]+"
    }
    
    
   static def getLatestPost(Long id) { 
        User.get(id).posts.first()
    }
    
  
   
}
