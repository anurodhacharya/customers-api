import { Button } from '@chakra-ui/react';
import SidebarWithHeader from './shared/SideBar';
import { useEffect } from 'react';
import { getCustomers } from './services/client';

const App = () => {
  useEffect(() => {
    getCustomers().then(res => {
      console.log(res);
    }).catch(err => {
      console.log(err);
    })
  }, [])

  return (
    <SidebarWithHeader>
      <Button colorScheme='teal' variant='outline'>Click Me</Button>
    </SidebarWithHeader>
  )
}

export default App;