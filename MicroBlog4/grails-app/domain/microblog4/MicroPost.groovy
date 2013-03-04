package microblog4

class MicroPost implements Comparable{
    String content
    User user
    Date postDate
    int compareTo(obj){
        postDate.compareTo(obj.postDate)
    }
    static belongsTo = {user: User}
    static constraints = {
        content size: 1..140 }
}
