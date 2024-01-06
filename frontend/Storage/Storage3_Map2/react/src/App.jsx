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

function App() {
  return (
    <div>
      { users.map((user, index) => {
        return <UserProfile
          name = { user.name }
          age = { user.age }
          gender = { user.gender }
      />
      }) }
    </div>
  )
}

export default App
