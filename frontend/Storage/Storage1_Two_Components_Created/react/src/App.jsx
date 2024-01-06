import UserProfile from "./UserProfile";

function App() {
  const brand = "Aurickcode";
  const counter = 0;
  return (
    <div>
      <UserProfile />
      <UserProfile />
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
