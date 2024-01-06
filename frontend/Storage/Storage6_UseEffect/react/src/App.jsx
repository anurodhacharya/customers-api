import UserProfile from "./UserProfile";
import { useState, useEffect } from "react";

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

const UserProfiles = ({ users}) => (
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

  const [counter, setCounter] = useState(0);
  const [isLoading, setIsLoading] = useState(false); 

  console.log("App Rendered - before useEffect, isLoading:", isLoading);

  useEffect(() => {
    setIsLoading(true);
    setTimeout(() => {
      setIsLoading(false);
    }, 4000)
  }, [])

  console.log("App Rendered - after useEffect, isLoading:", isLoading);

  if(isLoading) {
    console.log("Rendering Loading UI");
    return "loading...";
  }

  console.log("Rendering Main UI");
  return (
    <div>
      <button onClick={ () => setCounter(prevCounter => prevCounter + 1) }>Increment counter</button>
      <h1>{ counter }</h1>
      <UserProfiles users = { users }/>
    </div>
  )
}

export default App
