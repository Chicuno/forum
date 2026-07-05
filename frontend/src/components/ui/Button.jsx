import { cn } from "../../lib/utils"

const Button = ({ className, variant = "default", size = "default", ...props }) => {
  const variants = {
    default: "bg-sky-600 text-white hover:bg-sky-700",
    destructive: "bg-red-600 text-white hover:bg-red-700",
    outline: "border border-slate-700 bg-slate-900/70 text-slate-100 hover:bg-slate-800",
    secondary: "bg-slate-800 text-slate-100 hover:bg-slate-700",
    ghost: "text-slate-200 hover:bg-slate-800/80",
    link: "text-sky-400 underline-offset-4 hover:underline",
  }

  const sizes = {
    default: "h-10 px-4 py-2",
    sm: "h-9 rounded-md px-3",
    lg: "h-11 rounded-md px-8",
    icon: "h-10 w-10",
  }

  return (
    <button
      className={cn(
        "inline-flex items-center justify-center rounded-md text-sm font-medium transition-colors focus:outline-none focus:ring-2 focus:ring-sky-500 disabled:pointer-events-none disabled:opacity-50",
        variants[variant],
        sizes[size],
        className
      )}
      {...props}
    />
  )
}

export default Button
