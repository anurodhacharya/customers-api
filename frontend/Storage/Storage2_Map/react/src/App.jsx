import UserProfile from "./UserProfile";

function App() {
  const brand = "Aurickcode";
  const counter = 0;
  return (
    <div>
      <UserProfile
        name = {"Anurodh"}
        age = { 22 }
        gender = {"men"}
      >
      <p>Hello</p>
      </UserProfile>
      <UserProfile 
        name = {"Aurick"}
        age = { 24 }
        gender = {"women"}
      >
        <h1>Ciao</h1>
      </UserProfile>
      <h1>{ brand.toUpperCase() }</h1>
      <h2> { counter + 2 } </h2>
      <ol>
        <li>React</li>
        <li>Angular</li>
        <li>Vue</li>
        <li>Other</li>
      </ol>
    </div>
  )
}

export default App
