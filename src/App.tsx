import { Book, GraduationCap, Menu } from 'lucide-react'
import { Card } from './components/ui/card'
import { Label } from './components/ui/label'

export function HomePage() {
  return (
    <>
      <div className="flex flex-col items-center justify-center w-full h-dvh bg-gradient-to-bl from-zinc-900 to-zinc-950">
        <div className="flex items-center justify-start gap-6 w-full p-6 bg-opacity-30 backdrop-blur-md bg-white/10 fixed top-0 left-0">
          <Menu size={30} className="text-primary-foreground" />
          <Label className="text-2xl tracking-widest text-primary-foreground">
            Academico TE
          </Label>
        </div>
        <div className="w-full h-auto flex flex-col px-96">
          <Label className="w-full pb-4 text-primary-foreground text-2xl">
            Acessar como:
          </Label>
          <div className="flex w-full items-center justify-center gap-6">
            <Card className="w-full h-[300px] flex flex-col items-center justify-center bg-opacity-30 backdrop-blur-md bg-white/10 hover:scale-105 transition-all ">
              <GraduationCap
                size={80}
                className="text-blue-500"
                absoluteStrokeWidth
              />
              <Label className="text-xl text-primary-foreground">Aluno</Label>
            </Card>
            <Card className="w-full h-[300px] flex flex-col items-center justify-center bg-opacity-30 backdrop-blur-md bg-white/10 hover:scale-105 transition-all">
              <Book size={80} className="text-yellow-500" absoluteStrokeWidth />
              <Label className="text-xl text-primary-foreground">
                Professor
              </Label>
            </Card>
          </div>
        </div>
      </div>
    </>
  )
}
