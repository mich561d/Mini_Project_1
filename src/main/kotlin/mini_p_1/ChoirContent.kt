package mini_p_1

class ChoirContent (/* filename , ... */) : WebContent {
  fun getMember(): List<Member> =
    TODO(" Implement ␣GET ␣/ member ")

  fun getMember(id: Int): Member? =
    TODO(" Implement ␣GET ␣/ member /7")

  fun putMember(member: Member): Member =
    TODO(" Implement ␣PUT ␣/ member ")

  // ...
  override fun save() {
    TODO(" implement ␣ function ␣ save ")
  }
}