import { Outlet, Link, useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'
import Button from './ui/Button'
import { MessageSquare, BookOpen, Users, LogOut } from 'lucide-react'

const Layout = () => {
  const { user, logout } = useAuth()
  const navigate = useNavigate()
  const displayName = user?.nombre || user?.nombreUsuario || 'usuario'

  const handleLogout = () => {
    logout()
    navigate('/auth/login')
  }

  return (
    <div className="min-h-screen bg-transparent text-foreground">
      <nav className="border-b border-slate-800/80 bg-slate-950/80 backdrop-blur">
        <div className="container mx-auto px-4 py-4">
          <div className="flex items-center justify-between">
            <Link to="/" className="flex items-center space-x-2 text-2xl font-bold text-sky-400">
              <MessageSquare className="h-8 w-8" />
              <span>Forum</span>
            </Link>
            
            <div className="flex items-center space-x-4">
              <div className="text-sm text-slate-300">
                ¡Hola, {displayName}!
              </div>
              <div className="flex items-center space-x-2">
                <Link to="/cursos">
                  <Button variant="ghost" size="sm">
                    <BookOpen className="h-4 w-4 mr-2" />
                    Cursos
                  </Button>
                </Link>
                <Link to="/usuarios">
                  <Button variant="ghost" size="sm">
                    <Users className="h-4 w-4 mr-2" />
                    Usuarios
                  </Button>
                </Link>
                <Button variant="destructive" size="sm" onClick={handleLogout}>
                  <LogOut className="h-4 w-4 mr-2" />
                  Cerrar Sesión
                </Button>
              </div>
            </div>
          </div>
        </div>
      </nav>

      <main className="container mx-auto px-4 py-8">
        <Outlet />
      </main>
    </div>
  )
}

export default Layout
