import UserProfile from "./UserProfile";

const users = [
  {
    name: "Anurodh",
    age: 24,
    gender: "MALE"
  },
  {
    name: "James",
    age: 27,
    gender: "MALE"
  },
  {
    name: "Mary",
    age: 19,
    gender: "FEMALE"
  },
  {
    name: "Aurick",
    age: 22,
    gender: "MALE"
  }
]

const UserProfiles = ({ users }) => (
  <div>
      { users.map((user, index) => (
        <UserProfile
          key = { index }
          name = { user.name }
          age = { user.age }
          gender = { user.gender }
          imageNumber={ index }
      />
      )) }
    </div>
)

function App() {
  return (
    <UserProfiles users = { users }/>
  )
}

export default App
